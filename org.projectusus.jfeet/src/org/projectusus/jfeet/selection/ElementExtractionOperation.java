// Copyright (c) 2010 by Leif Frenzel
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
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
