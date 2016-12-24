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

package io._29cu.usmserver.core.model.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NaturalId
    private Application application;
    @ManyToOne
    @NaturalId
    private User user;

    @Column(name = "dateSubscribed")
    @Temporal(TemporalType.DATE)
    private Date dateSubscribed;

    @Column(name = "dateUnsubscribed")
    @Temporal(TemporalType.DATE)
    private Date dateUnsubscribed;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDateUnsubscribed(Date dateUnsubscribed) {
        this.dateUnsubscribed = dateUnsubscribed;
    }

    public Date getDateUnsubscribed() {
        return dateUnsubscribed;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {this.active = active;}

}
