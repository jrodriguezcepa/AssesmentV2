DELETE FROM datatypepavement;
--
-- Data for Name: datatypepavement; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO datatypepavement VALUES ('datatype.pavement.ok', 1);
INSERT INTO datatypepavement VALUES ('datatype.pavement.regular', 2);
INSERT INTO datatypepavement VALUES ('datatype.pavement.broken', 3);
INSERT INTO datatypepavement VALUES ('datatype.pavement.gravel', 4);
INSERT INTO datatypepavement VALUES ('datatype.pavement.repair', 5);


INSERT INTO datatypetrafficcontrols VALUES ('datatype.trafficcontrols.lights', 1);
INSERT INTO datatypetrafficcontrols VALUES ('datatype.trafficcontrols.police', 2);
INSERT INTO datatypetrafficcontrols VALUES ('datatype.trafficcontrols.roadsigns', 3);
INSERT INTO datatypetrafficcontrols VALUES ('datatype.trafficcontrols.other', 4);
INSERT INTO datatypetrafficcontrols VALUES ('datatype.trafficcontrols.none', 5);

DELETE FROM datatypeway;

--
-- Data for Name: datatypeway; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO datatypeway VALUES ('datatype.way.straight', 1);
INSERT INTO datatypeway VALUES ('datatype.way.bridge', 2);
INSERT INTO datatypeway VALUES ('datatype.way.curve', 3);
INSERT INTO datatypeway VALUES ('datatype.way.intersection', 4);
INSERT INTO datatypeway VALUES ('datatype.way.roundabout', 5);
INSERT INTO datatypeway VALUES ('datatype.way.other', 6);

DELETE FROM datatypezone;
--
-- Data for Name: datatypezone; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO datatypezone VALUES ('datatype.zone.populatedrural', 1);
INSERT INTO datatypezone VALUES ('datatype.zone.unpopulatedrural', 2);
INSERT INTO datatypezone VALUES ('datatype.zone.urban', 3);
INSERT INTO datatypezone VALUES ('datatype.zone.nodata', 4)


DELETE FROM datatypeaccidentfactors;

INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.tooclose',1);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.speed',2);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.ignoresigns',3);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.overtaking',4);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.turn',5);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.lights',6);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.stop',7);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.others',8);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.changelane',9);
INSERT INTO datatypeaccidentfactors values('datatype.accidentfactors.alcohol',10);

DELETE FROM datatypevehiclebehaviour;

INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.forward',1);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.overtaking',2);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.right',3);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.left',4);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.inout',5);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.uturn',6);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.backing',7);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.stuck',8);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.rightparked',9);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.wrongparked',10);
INSERT INTO datatypevehiclebehaviour values('datatype.vehiclebehaviour.lane',11);


DELETE FROM datatypecepaactivityschedulestate;

insert into datatypecepaactivityschedulestate values ('datatype.scheduledactivitystate.prevista','1');
insert into datatypecepaactivityschedulestate values ('datatype.scheduledactivitystate.prevista','2');
insert into datatypecepaactivityschedulestate values ('datatype.scheduledactivitystate.prevista','3');

DELETE FROM datatypecepaactivityscheduletype;
insert into datatypecepaactivityscheduletype values ('datatype.scheduledactivitytype','1')
insert into datatypecepaactivityscheduletype values ('datatype.scheduledactivitytype','2')
insert into datatypecepaactivityscheduletype values ('datatype.scheduledactivitytype','3')