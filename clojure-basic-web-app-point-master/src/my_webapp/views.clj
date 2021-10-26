(ns my-webapp.views
  (:require [my-webapp.db :as db]
            [my-webapp.h2 :as h2]
            [clojure.string :as str]
            [hiccup.page :as page]
            [ring.util.anti-forgery :as util]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "Locations:" title)]
   (page/include-css "/css/styles.css")])

(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"]
   " | "
   [:a {:href "/add-location"} "Add a Location"]
   " | "
   [:a {:href "/login"} "Login"]
   " | "
   [:a {:href "/all-locations"} "View All Locations"]
   " ]"])

(defn home-page
  []
  (page/html5
   (gen-page-head "Home")
   header-links
   [:h1 "Home"]
   [:p "Webapp to store and display some 2D (x, y)  locations."]))

(defn signup
  []
  (page/html5
   (gen-page-head "Sign Up")
   [:h1 "Sign Up"]
   [:form {:action "/signup" :method "POST"}
    (util/anti-forgery-field)
    [:p "Name: " [:input {:type "text" :name "name" :placeholder "Name"}]]
    [:p "Surname: " [:input {:type "text" :surname "surname" :placeholder "Surname"}]]
    [:p "Email: " [:input {:type "email" :name "email" :placeholder "Email"}]]
    [:p "Password: " [:input {:type "text" :name "password" :placeholder "Password"}]]
    [:p [:input {:type "submit" :value "Sign Up"}]]]))
(defn signup-results
  [{:keys [name surname email password]}]
  (let [id (h2/insert-account name surname email password)]
    (page/html5
     (gen-page-head "Account Added!")
     [:h1 "Account Added"]
     [:p "Congratulations " name ", your account has been successfully added. Now you can "
      [:a {:href "/login"} "login."]])))

(defn login-page
  []
  (page/html5
   (gen-page-head "Login App")
   [:h1 "Login"]
   [:form {:action "/login" :method "POST"}
    (util/anti-forgery-field)
    [:p [:input {:type "email" :name "email" :placeholder "Email"}]]
    [:p [:input {:type "password" :name "password" :placeholder "Password"}]]
    [:p [:input {:type "submit" :value "Log-in"}]]
    [:p [:a {:href "/signup"} "Sign Up"]][:p [:a {:href "/login-list"} "All Accounts"]]]))

(defn login-list-page
  []
  (let [all-acc (h2/get-all-accounts)]
    (page/html5
     (gen-page-head "All Accounts")
     header-links
     [:h1 "All Accounts"]
     [:table
      [:tr [:th "Name"] [:th "Email"] [:th "Password"]]
      (for [acc all-acc]
        [:tr [:td (:name acc)] [:td (:email acc)] [:td (:password acc)]])])))


(defn add-location-page
  []
  (page/html5
   (gen-page-head "Add a Location")
   header-links
   [:h1 "Add a Location"]
   [:form {:action "/add-location" :method "POST"}
    (util/anti-forgery-field)
    [:p "x value: " [:input {:type "text" :name "x"}]]
    [:p "y value: " [:input {:type "text" :name "y"}]]
    [:p [:input {:type "submit" :value "submit location"}]]]))

;; (defn add-location-results-page
;;   [{:keys [x y]}]
;;   (let [id (db/add-location-to-db x y)]
;;     (page/html5
;;     (gen-page-head "Added a Location")
;     header-links
;     [:h1 "Added a Location"]
;     [:p "Added [" x ", " y "] (id: " id ;") to the db. "
;      [:a {:href (str "/location/" id)} "See for yourself"]
;      "."])))

(defn location-page
  [loc-id]
  (let [{x :x y :y} (db/get-xy loc-id)]
    (page/html5
     (gen-page-head "All Locations in the db")
     header-links
     [:h1 "A Single Location"]
     [:p "id: " loc-id]
     [:p "x: " x]
     [:p "y: " y])))

(defn all-locations-page
  []
  (let [all-locs (db/get-all-locations)]
    (page/html5
     (gen-page-head "All Locations in the db")
     header-links
     [:h1 "All Locations"]
     [:table
      [:tr [:th "id"] [:th "x"] [:th "y"]]
      (for [loc all-locs]
        [:tr [:td (:id loc)] [:td (:x loc)] [:td (:y loc)]])])))

