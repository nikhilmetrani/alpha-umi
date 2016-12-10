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

package io._29cu.usmserver.controllers.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io._29cu.usmserver.controllers.rest.resources.CodesResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.CodesResourceAssembler;
import io._29cu.usmserver.core.model.entities.Category;
import io._29cu.usmserver.core.model.entities.Code;
import io._29cu.usmserver.core.service.CategoryService;
import io._29cu.usmserver.core.service.utilities.Codes;

@Controller
@RequestMapping("/api/1/codes")
public class CodeDefinitionController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<CodesResource> getCategoryCodes() {
        try {
            List<Category> categoryList = categoryService.getCategories();
            Codes codes = new Codes();
            for(Category category : categoryList) {
            	codes.add(new Code(category.getId().toString(), category.getName()));
            }
            CodesResource resource = new CodesResourceAssembler().toResource(codes);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(resource.getLink("self").getHref()));
            return new ResponseEntity<CodesResource>(resource, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<CodesResource>(HttpStatus.BAD_REQUEST);
        }
    }
}
