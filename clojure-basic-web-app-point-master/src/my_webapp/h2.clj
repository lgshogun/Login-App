(ns my-webapp.h2
  (:require [clojure.java.jdbc :as jdbc]))

(def h2 {:dbtype "h2" :dbname "./h2"})

(defn insert-account
  [name surname email password]
  (let [results (jdbc/insert! h2 :account {:name name :surname surname :email email :password password})]
    (assert (= (count results) 1))
    (first (vals (first results)))))

;(defn select-accounts
;  [read-acc]
;  (let [results (jdbc/query h2 
;                            ["select email, password from account where id=?" read-acc])]))

(defn get-all-accounts
  []
  (jdbc/query h2 "select name, email, password from account"))
