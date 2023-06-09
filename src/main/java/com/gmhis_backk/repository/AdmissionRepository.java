package com.gmhis_backk.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gmhis_backk.domain.Admission;
import com.gmhis_backk.domain.Examination;


/**
 * 
 * @author Hamed soumahoro
 *
 */
@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {


	@Query(value = "select * from  admission a order by a.created_at desc LIMIT 0,1", nativeQuery = true)
	public Admission findLastAdmission();

	@Modifying
	@Transactional
	@Query(value = "insert into admission_has_act ( admission_id, act_id,pratician_id, act_cost, bill_id, user_id ) values (:admission_id, :act_id, :pratician_id, :act_cost, :bill_id, :user_id)", nativeQuery = true)
	public void addActToAdmission(@Param("admission_id") Long admissionId, @Param("act_id") Long actId,
			@Param("pratician_id") Long particianId,  @Param("act_cost") int actCost,  @Param("bill_id") Long billId, @Param("user_id") Long userId);

	@Modifying
	@Transactional
	@Query(value = "delete from admission_has_act where admission_id = :admission_id and act_id = :act_id", nativeQuery = true)
	public void removeAdmissionAct(@Param("admission_id") Long admissionId, @Param("act_id") Long actId);

	@Modifying
	@Transactional
	@Query(value = "Update Admission a set admissionStatus='B' where a.id = :id")
	public void setAdmissionStatusToBilled(@Param("id") Long id);

	// admissions
	/****************************************************************************/
	@Query(value = "SELECT a FROM Admission a WHERE a.patient.firstName like %:firstName% and  (a.patient.lastName like %:lastName% ) and a.admissionStatus= :admissionStatus  AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByPatientName(@Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.admissionNumber like %:admissionNumber% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByAdmissionNumber(@Param("admissionNumber") String admissionNumber,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.patientExternalId like %:patientExternalId% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByPatientExternalId(@Param("patientExternalId") String patientExternalId,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.cellPhone1 like %:cellPhone% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByCellPhone(@Param("cellPhone") String cellPhone,
			@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.cnamNumber like %:cnamNumber% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByCnamNumber(@Param("cnamNumber") String cnamNumber,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.patient.idCardNumber like %:idCardNumber% and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByIdCardNumber(@Param("idCardNumber") String idCardNumber,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.practician.id = :practician and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByPractician(@Param("practician") Long practician,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.act.id = :act  and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByAct(@Param("act") Long act, @Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId,
			Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.service.id = :service and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByService(@Param("service") Long service,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.createdAt between :fromDate and :toDate and a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
			@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT a FROM Admission a WHERE a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissions(@Param("admissionStatus") String admissionStatus,@Param("facilityId") String facilityId, Pageable pageable);
	
	@Query(value = "SELECT a FROM Admission a WHERE a.admissionStatus= :admissionStatus AND a.facilityId =:facilityId")
	public Page<Admission> findAdmissionsByFacility(@Param("admissionStatus") String admissionStatus, @Param("facilityId") String facilityId, Pageable pageable);
	
	@Query(value = "SELECT a FROM Admission a WHERE a.createdAt BETWEEN :start AND :end AND a.facilityId =:facilityId")
	Page<Admission> findByDate(Date start, Date end,@Param("facilityId") String facilityId, Pageable pageable);

	// admissions queues
	/*******************************************************************************/

	@Query(value = "SELECT * FROM admission a, bill b, payment p, pratician pr, service s WHERE (a.id=b.admission_id and a.facility_id =:facilityId and b.id = p.bill_id and a.service_id = pr.speciality_id and s.waiting_room_id = :waiting_room and a.admission_status = 'B' and b.bill_status = 'C' and admission_end_date is null) or a.bail >=1000000 GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueue(Long waiting_room,@Param("facilityId") String facilityId, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, patient pa, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.patient_id = pa.id and a.service_id = pr.speciality_id and s.waiting_room_id = :waiting_room  and a.admission_status = 'B' and b.bill_status = 'C' and pa.first_name like %:firstName% and (pa.last_name like %:lastName% or pa.maiden_name like %:lastName%) and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByPatientName(String firstName, String lastName, Long waiting_room, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.service_id = pr.speciality_id and s.waiting_room_id = :waiting_room and a.admission_status = 'B' and b.bill_status = 'C' and a.admission_number like %:admissionNumber% and admission_end_date is null GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByAdmissionNumber(String admissionNumber, Long waiting_room,Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, patient pa, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.patient_id = pa.id  and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room  and a.admission_status = 'B' and b.bill_status = 'C' and pa.patient_external_id like %:patientExternalId% and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByPatientExternalId(String patientExternalId, Long waiting_room, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, patient pa, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.patient_id = pa.id  and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room  and a.admission_status = 'B' and b.bill_status = 'C' and (pa.cell_phone_1 like %:cellPhone% and pa.cell_phone_2 like %:cellPhone%) and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByCellPhone(String cellPhone,Long waiting_room, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, patient pa, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.patient_id = pa.id  and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room  and a.admission_status = 'B' and b.bill_status = 'C' and pa.cnam_number like %:cnamNumber% and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByCnamNumber(String cnamNumber, Long waiting_room, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, patient pa, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.patient_id = pa.id  and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room  and a.admission_status = 'B' and b.bill_status = 'C' and pa.idcard_number like %:idCardNumber% and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByIdCardNumber(String idCardNumber, Long waiting_room, Pageable pageable);
	
	@Query(value = "SELECT * FROM admission a, bill b, payment p, user u, pratician pr, service s WHERE a.id=b.admission_id and b.id = p.bill_id and a.user_id = u.id and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room  and a.admission_status = 'B' and b.bill_status = 'C' and u.id = :practician and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByPractician(Long practician, Long waiting_room, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, act ac, pratician pr, service s WHERE a.id=b.admission_id and b.id = p.bill_id and a.act_id = ac.id and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room and a.admission_status = 'B' and b.bill_status = 'C' and ac.id = :act and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByAct(Long act, Long waiting_room, Pageable pageable);

	@Query(value = "SELECT * FROM admission a, bill b, payment p, pratician pr, service s WHERE a.id=b.admission_id and b.id = p.bill_id and a.service_id = s.id and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room and a.admission_status = 'B' and b.bill_status = 'C' and s.id = :service and admission_end_date is null  GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionsInQueueByService(Long service, Long waiting_room, Pageable pageable);
	
	@Query(value = "SELECT * FROM admission a, bill b, payment p, pratician pr, service s  WHERE a.id=b.admission_id and b.id = p.bill_id and a.service_id = pr.specility_id and s.waiting_room_id = :waiting_room and a.admission_status = 'B' and b.bill_status = 'C' and a.created_at between :fromDate and :toDate and admission_end_date is null GROUP by a.id ", nativeQuery = true)
	public Page<Admission> findAdmissionInQueueByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Long waiting_room, Pageable pageable);
	
}
