package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_BLCL {
    private String message;
    private int status;
    private List<BoardDTO> data1;
    private List<CommentDTO> data2;

    public ResponseDTO_BLCL(String message, int status, List<BoardDTO> data1, List<CommentDTO> data2) {
        this.message = message;
        this.status = status;
        this.data1 =data1;
        this.data2 =data2;
    }
}