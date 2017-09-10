(ns spec-tools.spec-test
  (:require [clojure.test :refer [deftest are]]
            #?(:clj [clojure.future :as future]
               :cljs [cljs.core :as future])
            [spec-tools.spec :as spec]))

(deftest specs-test
  (are [spec pred]
    (= (:spec spec) pred)
    spec/any? future/any?
    spec/some? clojure.core/some?
    spec/number? clojure.core/number?
    spec/pos? clojure.core/pos?
    spec/neg? clojure.core/neg?
    spec/integer? clojure.core/integer?
    spec/int? future/int?
    spec/pos-int? future/pos-int?
    spec/neg-int? future/neg-int?
    spec/nat-int? future/nat-int?
    spec/float? clojure.core/float?
    spec/double? future/double?
    spec/boolean? future/boolean?
    spec/string? clojure.core/string?
    spec/ident? future/ident?
    spec/simple-ident? future/simple-ident?
    spec/qualified-ident? future/qualified-ident?
    spec/keyword? clojure.core/keyword?
    spec/simple-keyword? future/simple-keyword?
    spec/qualified-keyword? future/qualified-keyword?
    spec/symbol? clojure.core/symbol?
    spec/simple-symbol? future/simple-symbol?
    spec/qualified-symbol? future/qualified-symbol?
    spec/uuid? future/uuid?
    ;#?(:clj spec/uri? future/uri?)
    ;#?(:clj spec/bigdec? future/bigdec?)
    spec/inst? future/inst?
    spec/seqable? future/seqable?
    spec/indexed? future/indexed?
    spec/map? clojure.core/map?
    spec/vector? clojure.core/vector?
    spec/list? clojure.core/list?
    spec/seq? clojure.core/seq?
    spec/char? clojure.core/char?
    spec/set? clojure.core/set?
    spec/nil? clojure.core/nil?
    spec/false? clojure.core/false?
    spec/true? clojure.core/true?
    spec/zero? clojure.core/zero?
    ;#?(:clj spec/rational? clojure.core/rational?)
    spec/coll? clojure.core/coll?
    spec/empty? clojure.core/empty?
    spec/associative? clojure.core/associative?
    spec/sequential? clojure.core/sequential?
    ;#?(:clj spec/ratio? clojure.core/ratio?)
    ;#?(:clj spec/bytes? future/bytes?)
    ))
