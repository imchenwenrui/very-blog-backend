package com.very.blog.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long total;

    private List<T> records;

    public PageResult() {
    }

    private PageResult(Long total, List<T> records) {
        this.total = total;
        this.records = records;
    }

    public static <T> PageResult<T> of(Long total, List<T> records) {
        return new PageResult<>(total, records);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L, Collections.emptyList());
    }
}
