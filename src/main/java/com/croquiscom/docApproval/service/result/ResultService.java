package com.croquiscom.docApproval.service.result;

import java.util.List;

import org.springframework.stereotype.Service;

import com.croquiscom.docApproval.domain.result.ResultCommon;
import com.croquiscom.docApproval.domain.result.ResultList;
import com.croquiscom.docApproval.domain.result.ResultSingle;
import com.croquiscom.docApproval.domain.result.ResultType;

@Service
public class ResultService {
	public <E> ResultSingle<E> getResultDtoSingle(E data){
		ResultSingle<E> res = new ResultSingle<E>();
		res.setData(data);
		res.setStatus(ResultType.SUCCESS);
		
		return res;
	}
	
	public <T> ResultSingle<T> getResultSingle(T data){
		ResultSingle<T> res = new ResultSingle<T>();
		res.setData(data);
		res.setStatus(ResultType.SUCCESS);
		
		return res;
	}
	
	public <E> ResultList<E> getResultDtoList(List<E> data){
		ResultList<E> res = new ResultList<E>();
		res.setData(data);
		res.setStatus(ResultType.SUCCESS);
		
		return res;
	}
	
	public <T> ResultList<T> getResultList(List<T> data){
		ResultList<T> res = new ResultList<T>();
		res.setData(data);
		res.setStatus(ResultType.SUCCESS);
		
		return res;
	}
	
	public ResultCommon getSuccessResultStatus() {
		ResultCommon res = new ResultCommon();
		res.setStatus(ResultType.SUCCESS);
		
		return res;
	}
	
	public ResultCommon getFailResultStatus() {
		ResultCommon res = new ResultCommon();
		res.setStatus(ResultType.FAIL);
		
		return res;
	}
}
