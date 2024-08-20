package com.ddabong.tripflow.board.dao;

import com.ddabong.tripflow.board.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardRepository {
    void save(BoardDTO boardDTO);
    List<BoardDTO> findAll();
    //조회수 횟수를 update
    void updateHits(Long id);
    BoardDTO findById(Long id);
    List<BoardDTO>findLike(Long id);
    List<BoardDTO>findDetail(Long id);
    void update(BoardDTO boardDTO);
    void delete(Long id);
    //List<BoardDTO>findComment(Long id);
    List<CommentDTO>findComment(Long id);
    List<HashDTO>findHash(Long id);

    List<BoardDTO> findTOP();
    void saveComment(CommentDTO commentDTO);
    void savePost(BoardDTO boardDTO);
    void saveImage(ImageDTO imageDTO);
    void savePostImage(PostImageDTO postImageDTO);
    List<ImageDTO>findImage(Long id);

    PostImageDTO findPostid();
    Long findImageid();

    Long findMemberid(String s);

    void saveHash(HashDTO hashDTO);
    void saveHashJoin(HashDTO hashDTO);

    Long findHashid(String s);

}