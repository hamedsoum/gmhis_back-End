
-- INSERT INTO `user` (`id`, `user_id`, `first_name`, `last_name`, `username`, `password`, `email`, `tel`, `depot_id`, `profile_image_url`, `last_login_date`, `last_login_date_display`, `join_date`, `role`, `role_ids`, `authorities`, `is_active`, `is_not_locked`, `password_must_be_change`) VALUES
-- (0, '1234567890', 'Dabre', 'adjaratou', 'adabre265', '$2y$10$zbBsXPc6NpLaVbuTT9r4curoZJz/e12OrCaUesIHv8Fxpzjl0fFJa', 'adjaratoudabre22@gmail.com', '0506125572', 0, '', '2021-05-20 05:06:36', '2021-05-19 21:39:40', '2021-05-02 21:32:41', '', '0', 'tous', 1, 1, 0)

INSERT INTO `user` (`id`, `user_id`, `first_name`, `last_name`, `username`, `password`, `email`, `tel`, `depot_id`, `profile_image_url`, `last_login_date`, `last_login_date_display`, `join_date`, `role`, `role_ids`, `authorities`, `is_active`, `is_not_locked`, `password_must_be_change`) VALUES
(0, '1234567890', 'admin', 'system', 'adabre265', '$2y$10$zbBsXPc6NpLaVbuTT9r4curoZJz/e12OrCaUesIHv8Fxpzjl0fFJa', 'adjaratoudabre22gmail.com', '0506125572', 0, NULL, '2021-09-04 15:09:13', '2021-09-04 15:08:15', '2021-09-04 15:02:47', NULL, '0', 'tous', 1, 1, 1);

INSERT INTO `user_role`(`id`,`user_id`, `role_id`, `is_active`) VALUES (1, 0, 0, 1);