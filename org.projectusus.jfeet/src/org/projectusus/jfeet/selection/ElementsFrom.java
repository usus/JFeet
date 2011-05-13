// Copyright (c) 2010 by Leif Frenzel
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.jfeet.selection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/** Extract the elements (if any) from a JFace selection, in a type-safe
 * manner. */
public class ElementsFrom extends ElementExtractionOperation {

  /** constructs a new extraction operation.
   * 
   * @param selection a JFace selection object from which to extract the
   * selected elements. Can be <code>null</code>, in which case this extraction
   * operation yields empty results (but never <code>null</code>).
   */
  public ElementsFrom(ISelection selection) {
    super(selection);
  }

  /** retrieves the extracted elements typed as specified. If elements are contained
   *  in the list which don't comply to the specified type, they are ignored.
   * 
   * @param cls the class representing the expected type of the extracted element.
   * @param <T> the expected type of the extracted elements.
   * @return the elements under the expected type, or an empty list (never <code>null</code>)
   */
  public <T> List<T> as(Class<T> cls) {
    checkArg(cls);
    return extractElements(cls);
  }

  private <T> List<T> extractElements(Class<T> cls) {
    List<T> result = new ArrayList<T>();
    if (selectionIsGood()) {
      IStructuredSelection strusel = (IStructuredSelection) selection;
      for (Object element : strusel.toList()) {
        if (element instanceof IAdaptable) {
          element = ((IAdaptable) element).getAdapter(cls);
        }
        if (elementTypeIsGood(element, cls)) {
          result.add(cls.cast(element));
        }
      }
    }
    return result;
  }
}
