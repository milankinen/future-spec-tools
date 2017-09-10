(ns spec-tools.type
  (:require #?(:clj [clojure.future :refer :all])
            [spec-tools.impl :as impl]))

(defn- dispatch [x]
  (cond

    ;; symbol
    (symbol? x)
    (impl/clojure-core-symbol-or-any x)

    ;; a from
    (seq? x)
    (impl/clojure-core-symbol-or-any (first x))

    ;; default
    :else x))

(defmulti resolve-type dispatch :default ::default)

(defmethod resolve-type ::default [_] nil)

(defn types []
  #{:long
    :double
    :boolean
    :string
    :keyword
    :symbol
    :uuid
    :uri
    :bigdec
    :date
    :ratio
    :map
    :set
    :vector})

(defn type-symbols []
  (-> resolve-type
      methods
      keys
      (->> (filter symbol?))
      set))

(defmethod resolve-type #?(:cljs 'clojure.core/any? :clj 'clojure.future/any?) [_] nil)
(defmethod resolve-type 'clojure.core/some? [_] nil)
(defmethod resolve-type 'clojure.core/number? [_] :double)
(defmethod resolve-type 'clojure.core/integer? [_] :long)
(defmethod resolve-type #?(:cljs 'clojure.core/int? :clj 'clojure.future/int?) [_] :long)
(defmethod resolve-type #?(:cljs 'clojure.core/pos-int? :clj 'clojure.future/pos-int?) [_] :long)
(defmethod resolve-type #?(:cljs 'clojure.core/neg-int? :clj 'clojure.future/neg-int?) [_] :long)
(defmethod resolve-type #?(:cljs 'clojure.core/nat-int? :clj 'clojure.future/nat-int?) [_] :long)
(defmethod resolve-type 'clojure.core/float? [_] :double)
(defmethod resolve-type #?(:cljs 'clojure.core/double? :clj 'clojure.future/double?) [_] :double)
(defmethod resolve-type #?(:cljs 'clojure.core/boolean? :clj 'clojure.future/boolean?) [_] :boolean)
(defmethod resolve-type 'clojure.core/string? [_] :string)
(defmethod resolve-type #?(:cljs 'clojure.core/ident? :clj 'clojure.future/ident?) [_] :keyword)
(defmethod resolve-type #?(:cljs 'clojure.core/simple-ident? :clj 'clojure.future/simple-ident?) [_] :keyword)
(defmethod resolve-type #?(:cljs 'clojure.core/qualified-ident? :clj 'clojure.future/qualified-ident?) [_] :keyword)
(defmethod resolve-type 'clojure.core/keyword? [_] :keyword)
(defmethod resolve-type #?(:cljs 'clojure.core/simple-keyword? :clj 'clojure.future/simple-keyword?) [_] :keyword)
(defmethod resolve-type #?(:cljs 'clojure.core/qualified-keyword? :clj 'clojure.future/qualified-keyword?) [_] :keyword)
(defmethod resolve-type 'clojure.core/symbol? [_] :symbol)
(defmethod resolve-type #?(:cljs 'clojure.core/simple-symbol? :clj 'clojure.future/simple-symbol?) [_] :symbol)
(defmethod resolve-type #?(:cljs 'clojure.core/qualified-symbol? :clj 'clojure.future/qualified-symbol?) [_] :symbol)
(defmethod resolve-type #?(:cljs 'clojure.core/uuid? :clj 'clojure.future/uuid?) [_] :uuid)
#?(:clj (defmethod resolve-type 'clojure.future/uri? [_] :uri))
#?(:clj (defmethod resolve-type 'clojure.future/bigdec? [_] :bigdec))
(defmethod resolve-type #?(:cljs 'clojure.core/inst? :clj 'clojure.future/inst?) [_] :date)
(defmethod resolve-type #?(:cljs 'clojure.core/seqable? :clj 'clojure.future/seqable?) [_] nil)
(defmethod resolve-type #?(:cljs 'clojure.core/indexed? :clj 'clojure.future/indexed?) [_] nil)
(defmethod resolve-type 'clojure.core/map? [_] nil)
(defmethod resolve-type 'clojure.core/vector? [_] nil)
(defmethod resolve-type 'clojure.core/list? [_] nil)
(defmethod resolve-type 'clojure.core/seq? [_] nil)
(defmethod resolve-type 'clojure.core/char? [_] nil)
(defmethod resolve-type 'clojure.core/set? [_] nil)
(defmethod resolve-type 'clojure.core/nil? [_] :nil)
(defmethod resolve-type 'clojure.core/false? [_] :boolean)
(defmethod resolve-type 'clojure.core/true? [_] :boolean)
(defmethod resolve-type 'clojure.core/zero? [_] :long)
#?(:clj (defmethod resolve-type 'clojure.core/rational? [_] :long))
(defmethod resolve-type 'clojure.core/coll? [_] nil)
(defmethod resolve-type 'clojure.core/empty? [_] nil)
(defmethod resolve-type 'clojure.core/associative? [_] nil)
(defmethod resolve-type 'clojure.core/sequential? [_] nil)
#?(:clj (defmethod resolve-type 'clojure.core/ratio? [_] :ratio))
#?(:clj (defmethod resolve-type 'clojure.future/bytes? [_] nil))

(defmethod resolve-type :clojure.spec.alpha/unknown [_] nil)

(defmethod resolve-type 'clojure.spec.alpha/keys [_] :map)

(defmethod resolve-type 'clojure.spec.alpha/or [x] nil)

(defmethod resolve-type 'clojure.spec.alpha/and [x]
  (let [[_ predicate & _] x]
    (resolve-type predicate)))

; merge

(defmethod resolve-type 'clojure.spec.alpha/every [x]
  (let [{:keys [into]} (apply hash-map (drop 2 x))]
    (cond
      (map? into) :map
      (set? into) :set
      :else :vector)))

; every-ks

(defmethod resolve-type 'clojure.spec.alpha/coll-of [x]
  (let [{:keys [into]} (apply hash-map (drop 2 x))]
    (cond
      (map? into) :map
      (set? into) :set
      :else :vector)))

(defmethod resolve-type 'clojure.spec.alpha/map-of [_] :map)

; *
; +
; ?
; alt
; cat
; &
; tuple
; keys*
; nilable
