(ns poc.component.index
  (:require [hiccup.page :refer [include-css include-js]]
            [hiccup.core :as hiccup]))

;; To help avoid caching of CSS and JS when pushed to a server
(def js-loc (str "/js/main.js?" (java.util.UUID/randomUUID)))
(def css-loc (str "/css/main.css?" (java.util.UUID/randomUUID)))


;; Template
;; ##################################
(defn index* []
  (hiccup/html
    [:html
     [:head
      [:title "Gui!"]
      [:meta {:charset "utf-8"}]
      [:meta {:name    "viewport"
              :content "width=device-width, initial-scale=1.0"}]
      [:link {:rel "preconnect" :href "https://fonts.googleapis.com"}]
      [:link {:rel "preconnect" :href "https://fonts.gstatic.com" :crossorigin "true"}]
      [:link {:href "https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap" :rel "stylesheet"}]
      (include-css
        "style/login.css")]
     [:body
      [:div#app

       (include-js js-loc)]]]))


(def index (memoize index*))
