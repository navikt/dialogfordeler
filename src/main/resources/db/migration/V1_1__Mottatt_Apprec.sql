--ROLLBACK
----------
-- ALTER TABLE MELDING
--   DROP (
--   apprec_mottatt_tid
-- );
----------
--ROLLBACK

ALTER TABLE MELDING
  ADD (
  apprec_mottatt_tid TIMESTAMP
  );