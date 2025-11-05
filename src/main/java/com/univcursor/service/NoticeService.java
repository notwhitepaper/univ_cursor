package com.univcursor.service;

import com.univcursor.domain.Notice;
import com.univcursor.domain.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Page<Notice> getNotices(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return noticeRepository.findAll(pageable);
        }
        return noticeRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    public Notice getNotice(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다."));
    }
}
