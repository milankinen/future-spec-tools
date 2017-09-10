(ns spec-tools.conform
  #?(:cljs (:refer-clojure :exclude [Inst Keyword UUID]))
  (:require [clojure.spec.alpha :as s]
    #?@(:cljs [[goog.date.UtcDateTime]
               [goog.date.Date]]
        :clj [[clojure.future :refer :all]])
            [clojure.set :as set])
  #?(:clj
     (:import (java.util Date UUID)
              (java.time Instant))))

;;
;; Strings
;;

(defn string->long [_ x]
  (cond
    (int? x) x
    (string? x) (try
                  #?(:clj  (Long/parseLong x)
                     :cljs (let [x (js/parseInt x 10)]
                             (if (js/isNaN x) ::s/invalid x)))
                  (catch #?(:clj Exception, :cljs js/Error) _
                    ::s/invalid))
    :else ::s/invalid))

(defn string->double [_ x]
  (cond
    (double? x) x
    (string? x) (try
                  #?(:clj  (Double/parseDouble x)
                     :cljs (let [x (js/parseFloat x)]
                             (if (js/isNaN x) ::s/invalid x)))
                  (catch #?(:clj Exception, :cljs js/Error) _
                    ::s/invalid))
    :else ::s/invalid))

(defn string->keyword [_ x]
  (cond
    (keyword? x) x
    (string? x) (keyword x)
    :else ::s/invalid))

(defn string->boolean [_ x]
  (cond
    (boolean? x) x
    (= "true" x) true
    (= "false" x) false
    :else ::s/invalid))

(defn string->uuid [_ x]
  (cond
    (uuid? x) x
    (string? x) (try
                  #?(:clj  (UUID/fromString x)
                     ;; http://stackoverflow.com/questions/7905929/how-to-test-valid-uuid-guid
                     :cljs (if (re-find #"^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$" x)
                             (uuid x)
                             ::s/invalid))
                  (catch #?(:clj Exception, :cljs js/Error) _
                    ::s/invalid))
    :else ::s/invalid))

(defn string->date [_ x]
  (cond
    (inst? x) x
    (string? x)
    (try
      #?(:clj  (Date/from
                 (Instant/parse x))
         :cljs (js/Date. (.getTime (goog.date.UtcDateTime.fromIsoString x))))
      (catch #?(:clj  Exception
                :cljs js/Error) _
        ::s/invalid))
    :else ::s/invalid))

(defn string->symbol [_ x]
  (cond
    (symbol? x) x
    (string? x) (symbol x)
    :else ::s/invalid))

(defn string->nil [_ x]
  (cond
    (nil? x) nil
    (= "" x) nil
    :else ::s/invalid))

;;
;; Maps
;;

(defn strip-extra-keys [{:keys [keys]} x]
  (if (map? x)
    (select-keys x keys)
    x))

(defn fail-on-extra-keys [{:keys [keys]} x]
  (if (and (map? x) (not (set/subset? (-> x (clojure.core/keys) (set)) keys)))
    ::s/invalid
    x))

;;
;; type conforming
;;

(def json-type-conforming
  (merge
    {:keyword string->keyword
     :uuid string->uuid
     :date string->date
     :symbol string->symbol}
    #?(:clj
       {:uri nil
        :bigdec nil
        :ratio nil})))

(def string-type-conforming
  (merge
    json-type-conforming
    {:long string->long
     :double string->double
     :boolean string->boolean
     :nil string->nil
     :string nil}))

(def strip-extra-keys-type-conforming
  {:map strip-extra-keys})

(def fail-on-extra-keys-type-conforming
  {:map fail-on-extra-keys})
