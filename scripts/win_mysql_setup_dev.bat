REM # /**
REM #  * Copyright 2016 - 29cu.io and the authors of alpha-umi open source project

REM #  * Licensed under the Apache License, Version 2.0 (the "License");
REM #  * you may not use this file except in compliance with the License.
REM #  * You may obtain a copy of the License at

REM #  *     http://www.apache.org/licenses/LICENSE-2.0

REM #  * Unless required by applicable law or agreed to in writing, software
REM #  * distributed under the License is distributed on an "AS IS" BASIS,
REM #  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM #  * See the License for the specific language governing permissions and
REM #  * limitations under the License.
REM #  **/

rem execute the sql script to create DB

"C:\Program Files\MySQL\MySQL Server 5.6\bin\mysql" --user=root --password=root< setup.sql

pause