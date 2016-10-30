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

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @NaturalId
    private Application application;
    @ManyToOne
    @NaturalId
    private AuUser user;
    private String dateSubscribed;

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

    public AuUser getUser() {
        return user;
    }

    public void setUser(AuUser user) {
        this.user = user;
    }

    public String getDateSubscribed() {
        return dateSubscribed;
    }

    public void setDateSubscribed(String dateSubscribed) {
        this.dateSubscribed = dateSubscribed;
    }
}
