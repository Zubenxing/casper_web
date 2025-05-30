package com.example.casper_play.web_passwd.service;

import com.example.casper_play.web_passwd.entity.WebPasswd;
import com.example.casper_play.web_passwd.repository.WebPasswdRepository;
import com.example.casper_play.common.AesUtil;
import com.example.casper_play.jwt_auth.entity.User;
import com.example.casper_play.jwt_auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WebPasswdService {
    @Autowired
    private WebPasswdRepository webPasswdRepository;
    @Autowired
    private RoleRepository roleRepository;

    // 新增账号密码（需传入当前用户）
    public WebPasswd add(WebPasswd webPasswd, Long userId) {
        webPasswd.setUserId(userId);
        webPasswd.setPassword(AesUtil.encrypt(webPasswd.getPassword()));
        return webPasswdRepository.save(webPasswd);
    }

    // 分页查询账号密码列表（需传入当前用户id和角色名）
    public Page<WebPasswd> list(int page, int size, Long userId, String roleName) {
        if ("admin".equals(roleName) || "superadmin".equals(roleName)) {
            Page<WebPasswd> pageResult = webPasswdRepository.findAll(PageRequest.of(page, size));
            pageResult.forEach(item -> item.setPassword(AesUtil.decrypt(item.getPassword())));
            return pageResult;
        } else {
            Page<WebPasswd> all = webPasswdRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE));
            List<WebPasswd> filtered = all.getContent().stream()
                .filter(item -> item.getUserId().equals(userId))
                .collect(Collectors.toList());
            filtered.forEach(item -> item.setPassword(AesUtil.decrypt(item.getPassword())));
            int start = page * size;
            int end = Math.min(start + size, filtered.size());
            List<WebPasswd> pageList = start < end ? filtered.subList(start, end) : List.of();
            return new PageImpl<>(pageList, PageRequest.of(page, size), filtered.size());
        }
    }

    // 删除账号密码
    public void delete(Long id) {
        webPasswdRepository.deleteById(id);
    }
} 