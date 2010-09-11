package org.projectusus.jfeet.selection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.Test;
import org.projectusus.jfeet.selection.ElementsFrom;

public class ElementsFromTest {

	@Test
	public void singleSelectionYieldsElement() {
		ISelection selection = new StructuredSelection(asList("x"));
		List<String> result = new ElementsFrom(selection).as(String.class);
		assertThat(result.size(), is(1));
		assertThat(result, hasItem("x"));
	}

	@Test
	public void queryWithWrongTypeYieldsEmptyList() {
		ISelection selection = new StructuredSelection("x");
		List<Integer> empty = Collections.emptyList();
		assertThat(new ElementsFrom(selection).as(Integer.class), is(empty));
	}

	@Test
	public void multiSelectionYieldsAllElements() {
		ISelection selection = new StructuredSelection(asList("x", "y", "z"));
		List<String> result = new ElementsFrom(selection).as(String.class);
		assertThat(result.size(), is(3));
		assertThat(result, hasItem("x"));
		assertThat(result, hasItem("y"));
		assertThat(result, hasItem("z"));
	}

	@Test
	public void multiSelectionYieldsOnlyElementsOfCorrectType() {
		List<Object> elements = Arrays.asList("x", new Object());
		ISelection selection = new StructuredSelection(elements);
		List<String> result = new ElementsFrom(selection).as(String.class);
		assertThat(result.size(), is(1));
		assertThat(result, hasItem("x"));
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
		List<String> result = new ElementsFrom(selection).as(String.class);
		assertThat(result.size(), is(1));
		assertThat(result, hasItem("x"));
		List<Integer> empty = Collections.emptyList();
		assertThat(new ElementsFrom(selection).as(Integer.class), is(empty));
	}
}
