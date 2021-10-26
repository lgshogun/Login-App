(ns my-webapp.handler
  (:require [ring.adapter.jetty :as jetty]
            [my-webapp.views :as views] ; add this require
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:gen-class))

(defroutes app-routes
  (GET "/"
    []
    (views/home-page))

  (GET "/login"
    []
    (views/login-page))
  (POST "/login-select"
    {params :params}
    (views/login-select params))
  
  (GET "/login-list"
    []
    (views/login-list-page))

  (GET "/signup"
    []
    (views/signup))
  (POST "/signup"
    {params :params}
    (views/signup-results params))

  (GET "/add-location"
    []
    (views/add-location-page))
  (POST "/add-location"
    {params :params}
    (views/add-location-results-page params))
  (GET "/location/:loc-id"
    [loc-id]
    (views/location-page loc-id))
  (GET "/all-locations"
    []
    (views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main
  [& [port]]
  (let [port (Integer. (or port
			   (System/getenv "PORT")
		           5000))]
    (jetty/run-jetty #'app {:port port 
			    :join? false})))       
