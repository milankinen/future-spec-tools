(defproject milankinen/future-spec-tools "0.3.3"
  :description "Clojure(Script) tools for clojure-spec-tools"
  :url "https://github.com/milankinen/future-spec-tools"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"}
  :test-paths ["test/clj" "test/cljc"]
  :deploy-repositories [["releases" :clojars]]
  :codox {:output-path "doc"
          :source-uri "https://github.com/metosin/spec-tools/{version}/{filepath}#L{line}"
          :metadata {:doc/format :markdown}}

  :dependencies [[clojure-future-spec "1.9.0-alpha17" :exclusions [org.clojure/clojure]]]

  :profiles {:dev {:plugins [[jonase/eastwood "0.2.4"]
                             [lein-tach "0.3.0"]
                             [lein-doo "0.1.7"]
                             [lein-cljsbuild "1.1.7"]
                             [lein-cloverage "1.0.9"]
                             [lein-codox "0.10.3"]]
                   :jvm-opts ^:replace ["-server"]
                   ;:global-vars {*warn-on-reflection* true}
                   :dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.9.908"]
                                  [criterium "0.4.4"]
                                  [prismatic/schema "1.1.6"]
                                  [org.clojure/test.check "0.9.0"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [com.gfredericks/test.chuck "0.2.8"]
                                  [metosin/ring-swagger "0.24.2"]
                                  [metosin/scjsv "0.4.0"]]}
             :perf {:jvm-opts ^:replace ["-server"]}}
  :aliases {"all" ["with-profile" "dev"]
            "perf" ["with-profile" "default,dev,perf"]
            "test-clj" ["all" "do" ["test"] ["check"]]
            "test-phantom" ["doo" "phantom" "test"]
            "test-advanced" ["doo" "phantom" "advanced-test"]
            "test-node" ["doo" "node" "node-test"]}
  ;; Below, :process-shim false is workaround for <https://github.com/bensu/doo/pull/141>
  :cljsbuild {:builds [{:id "test"
                        :source-paths ["src" "test/cljc" "test/cljs"]
                        :compiler {:output-to "target/out/test.js"
                                   :output-dir "target/out"
                                   :main spec-tools.doo-runner
                                   :optimizations :none
                                   :process-shim false}}
                       {:id "advanced-test"
                        :source-paths ["src" "test/cljc" "test/cljs"]
                        :compiler {:output-to "target/advanced_out/test.js"
                                   :output-dir "target/advanced_out"
                                   :main spec-tools.doo-runner
                                   :optimizations :advanced
                                   :process-shim false}}
                       ;; Node.js requires :target :nodejs, hence the separate
                       ;; build configuration.
                       {:id "node-test"
                        :source-paths ["src" "test/cljc" "test/cljs"]
                        :compiler {:output-to "target/node_out/test.js"
                                   :output-dir "target/node_out"
                                   :main spec-tools.doo-runner
                                   :optimizations :none
                                   :target :nodejs
                                   :process-shim false}}]})
