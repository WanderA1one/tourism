package com.service;

import java.util.List;

public interface CommentService {
    List findyByScenicSpotId(String scenicSpotId);

    void saveComment(String text, String scenicSpotId);
}
