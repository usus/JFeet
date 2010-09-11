package org.projectusus.jfeet.selection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

abstract class ElementExtractionOperation {

  final ISelection selection;

  ElementExtractionOperation(ISelection selection){
    this.selection = selection;
  }

  <T> boolean elementTypeIsGood(Object element, Class<T> cls) {
    return element != null && cls.isAssignableFrom(element.getClass());
  }

  boolean selectionIsGood() {
    return selection instanceof IStructuredSelection && !selection.isEmpty();
  }

  <T> void checkArg(Class<T> cls) {
    if (cls == null) {
      throw new IllegalArgumentException();
    }
  }
}
