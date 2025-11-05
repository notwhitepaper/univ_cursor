package com.univcursor.web;

import com.univcursor.service.NoticeService;
import com.univcursor.web.dto.NoticeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public Page<NoticeResponse> list(@RequestParam(value = "q", required = false) String keyword,
                                     @PageableDefault(size = 20) Pageable pageable) {
        return noticeService.getNotices(keyword, pageable).map(NoticeResponse::from);
    }

    @GetMapping("/{id}")
    public NoticeResponse get(@PathVariable Long id) {
        return NoticeResponse.from(noticeService.getNotice(id));
    }
}
