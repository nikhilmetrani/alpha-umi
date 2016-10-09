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

package io._29cu.usmserver.common.utilities;

import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {
    private static SecurityHelper instance = null;

    private SecurityHelper() {}

    public static SecurityHelper getInstance() {
        if(null == instance)
            instance = new SecurityHelper();
        return instance;
    }

    public Boolean validateUserIdWithPrincipal(Long userId, UserService userService) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof String)) return false;

        User user = userService.findUserByPrincipal(principal.toString());
        return (user.getId() == userId);
    }
}
