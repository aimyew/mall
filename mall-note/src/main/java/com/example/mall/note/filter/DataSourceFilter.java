package com.example.mall.note.filter;

import com.example.mall.note.config.ChannelDataSourceConfig;
import com.example.mall.note.config.DynamicDataSourceContextHolder;
import com.example.mall.note.constants.Constants;
import com.example.mall.note.utils.SaasPrimaryChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "dataSourceFilter", urlPatterns = "/*")
public class DataSourceFilter implements Filter {

    @Resource
    private ChannelDataSourceConfig channelDataSourceConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String primaryChannelId = request.getHeader(Constants.PRIMARY_CHANNEL_ID);
        SaasPrimaryChannel.setPrimaryChannelId(primaryChannelId);
        String dateSource = channelDataSourceConfig.getChannelMap().get(primaryChannelId);
        DynamicDataSourceContextHolder.setDataSourceKey(StringUtils.hasText(dateSource) ? "primary" : dateSource);
        filterChain.doFilter(request, response);

        log.info("primaryChannelId : {} , dateSource : {}", primaryChannelId, dateSource);
    }

    @Override
    public void destroy() {

    }
}
