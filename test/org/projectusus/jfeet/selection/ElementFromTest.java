package org.projectusus.jfeet.selection;

import static org.eclipse.jface.viewers.StructuredSelection.EMPTY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;


import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.Test;
import org.projectusus.jfeet.selection.ElementFrom;

public class ElementFromTest {

	@Test(expected = IllegalArgumentException.class)
	public void typeMustBeSpecified() {
		new ElementFrom(null).as(null);
	}

	@Test
	public void nullSelectionYieldsNull() {
		assertThat(new ElementFrom(null).as(String.class), is(nullValue()));
		assertThat(new ElementFrom(null).asObject(), is(nullValue()));
	}

	@Test
	public void nonStructuredSelectionYieldsNull() {
		ISelection nonStructuredSelection = new ISelection() {
			public boolean isEmpty() {
				return false;
			}
		};
		ElementFrom operation = new ElementFrom(nonStructuredSelection);
		assertThat(operation.as(String.class), is(nullValue()));
		assertThat(operation.asObject(), is(nullValue()));
	}

	@Test
	public void emptySelectionYieldsNull() {
		assertThat(new ElementFrom(EMPTY).as(String.class), is(nullValue()));
		assertThat(new ElementFrom(EMPTY).asObject(), is(nullValue()));
	}

	@Test
	public void singleSelectionYieldsElement() {
		Object element = "x";
		ISelection selection = new StructuredSelection(element);
		assertThat(new ElementFrom(selection).as(String.class), is(element));
		assertThat(new ElementFrom(selection).asObject(), is(element));
	}

	@Test
	public void queryWithWrongTypeYieldsNull() {
		ISelection selection = new StructuredSelection("x");
		assertThat(new ElementFrom(selection).as(Integer.class),
				is(nullValue()));
	}

	@Test
	public void multiSelectionYieldsFirstElement() {
		Object element = "x";
		List<Object> elements = Arrays.asList(element, "y", "z");
		ISelection selection = new StructuredSelection(elements);
		assertThat(new ElementFrom(selection).as(String.class), is(element));
		assertThat(new ElementFrom(selection).asObject(), is(element));
	}

	@Test
	public void adaptableSelectionYieldsElement() {
		final Object element = "x";
		IAdaptable adapted = new IAdaptable() {
			@SuppressWarnings("unchecked")
			public Object getAdapter(Class adapter) {
				return adapter == String.class ? element : null;
			}
		};
		ISelection selection = new StructuredSelection(adapted);

		assertThat(new ElementFrom(selection).as(String.class), is(element));
		assertThat(new ElementFrom(selection).asObject(), is(nullValue()));
	}
}
