(ns poc.component.left-menu
  (:require
      [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
      [com.fulcrologic.fulcro.dom :as dom :as dom ]))

(defsc LeftMenu
  [this props]
  (dom/div :.menu
           (dom/ul
             (dom/li "Item 1")
             (dom/li "Item 2")
             (dom/li "Item 3"))))

(def left-menu (comp/factory LeftMenu))