(ns poc.component.login-form
  (:require                                                 ;; Fulcro
    [com.fulcrologic.fulcro.ui-state-machines :as uism]
    [com.fulcrologic.fulcro.dom :as dom :refer [div label input button]]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc factory]]
    [com.fulcrologic.fulcro.mutations :as m :refer [set-string!]]
    [com.fulcrologic.fulcro-css.css :as css]))

(defsc LoginForm
  [this {:ui/keys [email password bad-credentials]}]
  {:ident         (fn [] [:component/id :login])
   :query         [:ui/email :ui/password :ui/bad-credentials
                   [::uism/asm-id :poc.session.user/session]]
   :route-segment ["login"]
   :initial-state {:ui/email           "erasmo@example.com"
                   :ui/password        "letmein"
                   :ui/bad-credentials false}
   :css           [[:.box {:margin ""}]]}
  (let [{:keys [box]} (css/get-classnames LoginForm)
        session-state (uism/get-active-state this :poc.session.user/session)
        busy? (= session-state :state/checking-credentials)
        error? (= session-state :state/server-failed)]
    (div {:classes [box]}
         (dom/img {:src "imgs/clojureLogo.svg"})
         (when error? "ERROR!")
         (when bad-credentials "BAD CREDENTIALS!")
         (when busy? "Loading…")
         (dom/div :.box
                   (div :.field
                        (input {:value    email
                                :disabled busy?
                                :onChange #(set-string! this :ui/email :event %)}))
                  (div :.field
                        (input {:value    password
                                :disabled busy?
                                :onChange #(set-string! this :ui/password :event %)})
                        (button {:onClick #(uism/trigger! this :poc.session.user/session :event/login {:contact/email email
                                                                                                       :user/password password})}
                                "Login"))
                   )

         )))


(def login-form (factory LoginForm {:keyfn :component/id}))