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

import java.util.ArrayList;
import java.util.List;

public class AppHelper {

    private static AppHelper instance = null;

    private AppHelper() {}

    public static AppHelper getInstance() {
        if(null == instance)
            instance = new AppHelper();
        return instance;
    }

    /**
     * Converting iterable to List
     * @param iter
     * @return
     */
    public <E> List<E> convertIterableToList(Iterable<E> iter) {
        List<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    /**
     * Replace all single quotes with double quotes.
     * @param source
     * @return
     */
    public static String escapeSQLString(String source) {
    	if(source != null) {
    		return source.replaceAll("'", "''");
    	}
    	return source;
    }
}
