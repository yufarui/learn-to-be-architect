package indi.biz.yufr.select;

import lombok.Data;

@Data
public class SelectOption<E> {

    private String key;

    private String value;

    private E data;

}
