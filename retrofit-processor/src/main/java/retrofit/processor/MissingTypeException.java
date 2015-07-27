/*
 * Copyright (C) 2014 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit.processor;

/**
 * Exception thrown in the specific case where processing of a class was abandoned because it
 * required types that the class references to be present and they were not. This case is handled
 * specially because it is possible that those types might be generated later during annotation
 * processing, so we should reattempt the processing of the class in a later annotation processing
 * round.
 *
 * @author Éamonn McManus
 */
@SuppressWarnings("serial")
class MissingTypeException extends RuntimeException {
}
