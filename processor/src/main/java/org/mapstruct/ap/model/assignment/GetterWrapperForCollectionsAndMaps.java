/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.model.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.util.Strings;

/**
 * This wrapper handles the situation were an assignment must be done via a target getter method because there
 * is no setter available.
 *
 * The wrapper checks if there is an collection or map initialized on the target bean (not null). If so it uses the
 * addAll (for collections) or putAll (for maps). The collection / map is cleared in case of a pre-existing target
 * {@link org.mapstruct.MappingTarget }before adding the source entries. The goal is that the same collection / map
 * is used as target.
 *
 * Nothing can be added if the getter on the target returns null.
 *
 * @author Sjaak Derksen
 */
public class GetterWrapperForCollectionsAndMaps extends AssignmentWrapper {

    private final List<Type> exceptionTypesToExclude;
    private final Type targetType;
    private final String localVarName;


    public GetterWrapperForCollectionsAndMaps(Assignment decoratedAssignment, List<Type> exceptionTypesToExclude,
        Type targetType, Collection<String> existingVariableNames ) {
        super( decoratedAssignment );
        this.exceptionTypesToExclude = exceptionTypesToExclude;
        this.targetType = targetType;
        this.localVarName = Strings.getSaveVariableName( "target" + targetType.getName(), existingVariableNames );
        existingVariableNames.add( localVarName );
   }

    @Override
    public List<Type> getExceptionTypes() {
        List<Type> parentExceptionTypes = super.getExceptionTypes();
        List<Type> result = new ArrayList<Type>( parentExceptionTypes );
        for ( Type exceptionTypeToExclude : exceptionTypesToExclude ) {
            for ( Type parentExceptionType : parentExceptionTypes ) {
                if ( parentExceptionType.isAssignableTo( exceptionTypeToExclude ) ) {
                    result.remove( parentExceptionType );
                }
            }
        }
        return result;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<Type>();
        imported.addAll( super.getImportTypes() );
        imported.add( targetType ); /* is a local var */
        return imported;
    }

    public String getLocalVarName() {
        return localVarName;
    }

}