package com.jani.forumhub.web.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Deque<Long>> hits = new ConcurrentHashMap<>();
    private final int limit = 100;
    private final long windowMs = 60_000L;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        long now = Instant.now().toEpochMilli();
        var q = hits.computeIfAbsent(ip, k -> new ArrayDeque<>());
        synchronized (q){
            while (!q.isEmpty() && (now - q.peekFirst()) > windowMs) q.pollFirst();
            if (q.size() >= limit){
                response.setStatus(429);
                response.getWriter().write("{"error":"Too Many Requests"}");
                return;
            }
            q.addLast(now);
        }
        filterChain.doFilter(request, response);
    }
}
