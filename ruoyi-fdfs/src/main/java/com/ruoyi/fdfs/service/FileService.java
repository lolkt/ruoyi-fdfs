package com.ruoyi.fdfs.service;

import com.ruoyi.fdfs.domain.RedisConnectException;
import com.ruoyi.fdfs.domain.RespMsgBean;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    RespMsgBean checkFile(Long userId, String fileMd5) throws RedisConnectException;

    RespMsgBean uploadBigFileChunk(MultipartFile file, Long userId, String fileMd5, String fileName, Integer totalChunks, Integer chunkNumber, Integer currentChunkSize, String bizId, String bizCode) throws RedisConnectException;
}
