package com.cloudxhoster.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface TransFormer<I, O> {

    O toSingle(I item);

    default List<O> toList(List<I> items) {
        if (items == null || items.isEmpty()) return new ArrayList<>();
        return items.stream()
                .filter(Objects::nonNull)
                .map(this::toSingle)
                .collect(Collectors.toList());
    }
}
