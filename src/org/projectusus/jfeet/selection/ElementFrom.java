package org.projectusus.jfeet.selection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/** Extract the first element (if any) from a JFace selection, in a type-safe
 * manner. */
public class ElementFrom extends ElementExtractionOperation {


  /** constructs a new extraction operation.
   * 
   * @param selection a JFace selection object from which to extract the
   * selected element. Can be <code>null</code>, in which case this extraction
   * operation yields <code>null</code>.
   */
  public ElementFrom(ISelection selection) {
    super(selection);
  }

  /** retrieves the extracted element typed as specified.
   * 
   * @param cls the class representing the expected type of the extracted element.
   * @param <T> the expected type of the extracted element.
   * @return the element under the expected type, or <code>null</code>
   */
  public <T> T as(Class<T> cls) {
    checkArg(cls);
    return extractElement(cls);
  }

  /** retrieves the extracted element, untyped.
   * 
   * @return the element, or <code>null</code>
   */
  public Object asObject() {
    return as(Object.class);
  }

  private <T> T extractElement(Class<T> cls) {
    T result = null;
    if (selectionIsGood()) {
      Object element = readFromSelection(cls);
      if (elementTypeIsGood(element, cls)) {
        result = cls.cast(element);
      }
    }
    return result;
  }

  private <T> Object readFromSelection(Class<T> cls) {
    Object element = ((IStructuredSelection) selection).getFirstElement();
    if (element instanceof IAdaptable) {
      element = ((IAdaptable) element).getAdapter(cls);
    }
    return element;
  }
}
