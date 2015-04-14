package com.comdo.zf_agent_a_pad.entity;
/***
 * 
*    
*
* @version    
*
 */
public class MessageEntity {
	private String content;
	private long id;
	private String title;
	private String create_at;
	private Boolean ischeck;
	public MessageEntity(String content,long id,String title,
			String create_at,Boolean ischeck){
		super();
		this.content=content;
		this.id=id;
		this.title=title;
		this.create_at=create_at;
		this.ischeck=ischeck;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreate_at() {
		return create_at;
	}
	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}
	public Boolean getIscheck() {
		return ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		this.ischeck = ischeck;
	}
	@Override
	public String toString() {
		return "MessageEntity [content=" + content + ", id=" + id + ", title="
				+ title + ", create_at=" + create_at + ", ischeck=" + ischeck
				+ "]";
	}
	
	
	
	
	
}
