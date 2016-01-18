package com.github.alexmojaki.caseclasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class PrettyTableBuilder extends AbstractResultBuilder {

    static <E extends CaseClass> String getPrettyTable(Iterable<E> objects) {
        Collection<E> objectCollection = toCollection(objects);
        if (objectCollection.isEmpty()) {
            return "[Empty table]";
        }

        return new PrettyTableBuilder().getString(objectCollection);
    }

    private static <E extends CaseClass> Collection<E> toCollection(Iterable<E> objects) {
        if (objects instanceof Collection) {
            return (Collection<E>) objects;
        }

        List<E> list = new ArrayList<E>();
        for (E object : objects) {
            list.add(object);
        }
        return list;
    }

    private <E extends CaseClass> String getString(Collection<E> objects) {
        names = CaseClasses.getNameList(objects.iterator().next());
        widths = new int[names.size()];
        for (int i = 0; i < names.size(); i++) {
            widths[i] = names.get(i).length();
        }
        for (CaseClass object : objects) {
            object.buildResult(widthsBuilder);
        }

        int rowWidth = 1;  // trailing '|' ('\n' not included)
        for (int width : widths) {
            rowWidth += width + 3;  // "| " + value + " "
        }
        int numRows = 4 + objects.size();  // 3 rows of lines and 1 header row
        int stringSize = (rowWidth + 1) * numRows;  // '\n' included
        stringBuilder =  new StringBuilder(stringSize);

        appendLine();
        for (String name : names) {
            appendCell(name, false);
        }
        stringBuilder.append("|\n");
        appendLine();

        for (CaseClass object : objects) {
            object.buildResult(this);
            stringBuilder.append("|\n");
        }

        appendLine();

        return stringBuilder.toString();
    }

    private int[] widths;
    private StringBuilder stringBuilder;
    private int column = 0;
    List<String> names;

    private void incColumn() {
        column = (column + 1) % widths.length;
    }

    private void appendMany(int num, char c) {
        for (int i = 0; i < num; i++) {
            stringBuilder.append(c);
        }
    }

    private void appendLine() {
        for (int width : widths) {
            stringBuilder.append('+');
            appendMany(width + 2, '-');
        }
        stringBuilder.append("+\n");
    }

    private void appendCell(String string, boolean alignRight) {
        stringBuilder.append("| ");
        int numSpaces = widths[column] - string.length();
        incColumn();
        if (alignRight) {
            appendMany(numSpaces, ' ');
            stringBuilder.append(string);
        } else {
            stringBuilder.append(string);
            appendMany(numSpaces, ' ');
        }
        stringBuilder.append(' ');
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        appendCell(String.valueOf(value), value instanceof Number);
    }

    private ResultBuilder widthsBuilder = new AbstractResultBuilder() {

        @Override
        protected void simpleAdd(String name, Object value) {
            String expectedName = names.get(column);
            if (!name.equals(expectedName)) {
                throw new IllegalArgumentException("Found the name " + name + ", expected " + expectedName +
                        ". Ensure that every value has the same sequence of names.");
            }
            widths[column] = Math.max(widths[column], String.valueOf(value).length());
            incColumn();
        }
    };
}
