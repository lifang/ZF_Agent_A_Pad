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
	private int id;
	private String title;
	private String create_at;
	private boolean status;
	public MessageEntity(String content,int id,String title,
			String create_at,boolean status){
		super();
		this.content=content;
		this.id=id;
		this.title=title;
		this.create_at=create_at;
		this.status=status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MessageEntity [content=" + content + ", id=" + id + ", title="
				+ title + ", create_at=" + create_at + ", status=" + status
				+ "]";
	}
	
	
	
	
	
}
