# future-spec-tools 

My best effort to port [`spec-tools`](https://github.com/metosin/spec-tools) to
Clojure 1.8 and [`clojure-future-spec`](https://github.com/tonsky/clojure-future-spec).

[![Clojars Project](http://clojars.org/milankinen/future-spec-tools/latest-version.svg)](http://clojars.org/milankinen/future-spec-tools)

[![Build status](https://img.shields.io/travis/milankinen/future-spec-tools/clojure-1.8.svg?style=flat-square)](https://travis-ci.org/milankinen/future-spec-tools)

## Usage

In order to use this package, you must also include `clojure-future-spec` and 
`clojure/test.check` in your dependencies:

```clj 
(defproject your-project "0.0.1"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]
                 [org.clojure/test.check "0.10.0-alpha2"]
                 [clojure-future-spec "1.9.0-alpha17"]
                 [milankinen/future-spec-tools "0.3.3-SNAPSHOT"]]
  ...)
```

## Documentation

Please see [the official GitHub project](https://github.com/metosin/spec-tools).

## License

Eclipse Public License 1.0, the same as Clojure.
