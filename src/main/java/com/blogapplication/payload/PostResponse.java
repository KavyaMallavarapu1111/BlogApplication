package com.blogapplication.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Schema( description = "PostResponse")
public class PostResponse {

    @Schema(description ="content")
    private List<PostDto> content;
    @Schema(description ="page no")
    private int pageNo;
    @Schema(description ="page size")
    private int pageSize;
    @Schema(description = "total elements")
    private long totalElements;
    @Schema(description ="total pages")
    private int totalPages;
    @Schema(description =" is last page")
    private boolean last;

    public List<PostDto> getContent() {
        return content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setContent(List<PostDto> content) {
        this.content = content;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

}
