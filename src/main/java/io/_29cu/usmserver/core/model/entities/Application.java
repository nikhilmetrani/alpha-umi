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

import io._29cu.usmserver.core.model.enumerations.AppState;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Application extends ApplicationBase{
	
    @NotNull
    private AppState state;
    
    @ManyToOne
    private User developer;
    
    @ManyToOne
    private Category category;
    
    @OneToMany(mappedBy="application")
    private List<Review> reviews;
    
    @OneToMany(mappedBy="application")
    private List<Rate> ratings;    

    public List<Rate> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rate> ratings) {
		this.ratings = ratings;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AppState getState() { return state; }

    public void setState(AppState state) { this.state = state; }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

}
