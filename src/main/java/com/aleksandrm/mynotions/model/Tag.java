package com.aleksandrm.mynotions.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private int id;
    private String name;
    // optional
    // private List<Note> notes;
}
