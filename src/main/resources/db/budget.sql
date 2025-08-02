
--2025-08-02 15:50 - Portugal - Isaias Lima - Create an event for remove expired budgets for 15 days.
CREATE EVENT remove_expired_budgets
ON SCHEDULE EVERY 1 DAY
STARTS NOW() + INTERVAL 5 MINUTE
ON COMPLETION PRESERVE
ENABLE
COMMENT 'Remove expired budgets for 15 days'
DO begin
	update budgets set bstatus = 2 where bstatus = 0 and datediff(now(), openingdate) >= 15;
end;