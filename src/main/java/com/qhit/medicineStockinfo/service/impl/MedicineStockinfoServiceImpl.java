package com.qhit.medicineStockinfo.service.impl;

import com.qhit.medicineInstock.pojo.MedicineInstock;
import com.qhit.medicineStockinfo.service.IMedicineStockinfoService;
import java.util.List;
import com.qhit.medicineStockinfo.dao.IMedicineStockinfoDao;
import com.qhit.medicineStockinfo.dao.impl.MedicineStockinfoDaoImpl;
import com.qhit.medicineStockinfo.pojo.MedicineStockinfo;

/**
* Created by GeneratorCode on 2018/12/16
*/

public class MedicineStockinfoServiceImpl  implements IMedicineStockinfoService {

    IMedicineStockinfoDao dao = new MedicineStockinfoDaoImpl();

    @Override 
    public boolean insert(Object object) { 
        return dao.insert(object); 
    } 


    @Override 
    public boolean update(Object object) { 
        return dao.update(object); 
    } 


    @Override 
    public boolean updateSelective(Object object) { 
        return dao.updateSelective(object); 
    } 


    @Override 
    public boolean delete(Object id) { 
        MedicineStockinfo medicineStockinfo = findById(id); 
        return dao.delete(medicineStockinfo); 
    } 


    @Override 
    public List findAll() { 
        return dao.findAll(); 
    } 


    @Override 
    public MedicineStockinfo findById(Object id) { 
        List<MedicineStockinfo> list = dao.findById(id); 
        return  list.get(0); 
    } 


    @Override 
    public boolean freeUpdate(String sql) {
        return dao.freeUpdate(sql);
    }


    @Override 
    public List<MedicineStockinfo> search(MedicineStockinfo medicineStockinfo) {
            String sql = "select * from medicine_stockinfo where 1=1 "; 
            if (medicineStockinfo.getMedicinecodeId()!=null && !"".equals(medicineStockinfo.getMedicinecodeId())){        
                sql+=" and medicinecode_id like '%"+medicineStockinfo.getMedicinecodeId()+"%' ";        
            } 
            if (medicineStockinfo.getAmt()!=null && !"".equals(medicineStockinfo.getAmt())){        
                sql+=" and amt like '%"+medicineStockinfo.getAmt()+"%' ";        
            } 
            if (medicineStockinfo.getUnitprc()!=null && !"".equals(medicineStockinfo.getUnitprc())){        
                sql+=" and unitprc like '%"+medicineStockinfo.getUnitprc()+"%' ";        
            } 
            if (medicineStockinfo.getSaleunitprc()!=null && !"".equals(medicineStockinfo.getSaleunitprc())){        
                sql+=" and saleunitprc like '%"+medicineStockinfo.getSaleunitprc()+"%' ";        
            } 
            if (medicineStockinfo.getZje()!=null && !"".equals(medicineStockinfo.getZje())){        
                sql+=" and zje like '%"+medicineStockinfo.getZje()+"%' ";        
            } 
            List<MedicineStockinfo> list = dao.freeFind(sql);        
            return list;        
    }

    @Override
    public void stockinfo() {
        MedicineStockinfoServiceImpl stockinfoService = new MedicineStockinfoServiceImpl();
        List<MedicineStockinfo> list1 = dao.findAll();
        if (list1!=null&&list1.size()>0){
            for (MedicineStockinfo list:list1){
                stockinfoService.delete(list.getStockinfoId());
            }
        }
        MedicineStockinfo stockinfo = new MedicineStockinfo();
        String sql = "SELECT *,CAST(SUM(inamt) AS CHAR) AS sums from medicine_instock GROUP BY medicine_codeid";
        List<MedicineInstock> list = dao.freeFind(sql);
        for (MedicineInstock instock:list){
            stockinfo.setMedicinecodeId(instock.getMedicineCodeid());
            stockinfo.setAmt(Integer.valueOf(instock.getSums()));
            stockinfo.setSaleunitprc(instock.getUnitprc()*1.5);
            stockinfo.setUnitprc(instock.getUnitprc());
            stockinfo.setZje(instock.getUnitprc()*Integer.valueOf(instock.getSums()));
            dao.insert(stockinfo);
        }
    }

    @Override
    public List<MedicineStockinfo> find() {
        String sql = "SELECT * from medicine_stockinfo ms JOIN medicine_code mc ON ms.medicinecode_id=mc.code_id";
        return dao.freeFind(sql);
    }

    @Override
    public MedicineStockinfo findByCodeId(int codeId) {
        List<MedicineStockinfo> byMedicinecodeId = dao.findByMedicinecodeId(codeId);
        return byMedicinecodeId.get(0);
    }


}