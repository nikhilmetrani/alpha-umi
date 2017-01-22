/**
 * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io._29cu.usmserver.controllers.rest.resources;

import java.util.Date;

import io._29cu.usmserver.core.model.entities.*;


public class SubscriptionResource extends EntityResourceBase<Subscription>{
    private Long rid;
    private Application application;
    private User user;
    private Date dateSubscribed;
    private Date dateUnsubscribed;

    private Boolean active;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(Date dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }

    public Date getDateUnsubscribed() {
        return dateUnsubscribed;
    }

    public void setDateUnsubscribed(Date dateUnsubscribed) {
        this.dateUnsubscribed = dateUnsubscribed;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public Subscription toEntity() {
        Subscription subscription = new Subscription();
        subscription.setApplication(application);
        subscription.setUser(user);
        subscription.setDateSubscribed(dateSubscribed);
        subscription.setDateUnsubscribed(dateUnsubscribed);
        subscription.setActive(active);
        return subscription;
    }
}
