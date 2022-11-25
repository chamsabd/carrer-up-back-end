package com.project.sessionserver.entities.value_object;

import com.project.sessionserver.entities.Session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class ResponseTemplateVO {
	
	private Session session;
	 private Formation formation;
	 
	public ResponseTemplateVO(Session session, Formation formation) {
		super();
		this.session = session;
		this.formation = formation;
	}
	 
	 
	 
}
