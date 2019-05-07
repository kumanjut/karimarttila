(ns mydev
  (:require
    [clojure.tools.logging :as log]
    [clojure.tools.namespace.repl :as ns-repl]
    [mount.core]
    [ring.adapter.jetty :refer [run-jetty]]
    [simpleserver.webserver.server :as ws]
    [clj-http.client :as http-client]
    ))

; See defstate in simpleserver.webserver.server.

(defn start
  "Starts application state."
  []
  (log/debug "ENTER start")
  (mount.core/start))

(defn stop
  "Stops application state."
  []
  (log/debug "ENTER stop")
  (mount.core/stop)
  )

(defn refresh
  "Refreshes REPL, does not start server."
  []
  (log/debug "ENTER refresh")
  (stop)
  (ns-repl/refresh))

(defn reset []
  "Resets REPL and starts server."
  (log/debug "ENTER reset")
  (stop)
  (ns-repl/refresh :after 'mydev/start))

; Example: (mydev/curl-get "info")
(defn curl-get
  "A helper function to query the APIs in REPL (you don't have to jump to IDEA terminal and back to REPL)"
  [path]
  (log/debug "ENTER curl-get")
  (select-keys
    (http-client/get (str "http://localhost:6060/" path) {:as :json})
    [:status :body]))