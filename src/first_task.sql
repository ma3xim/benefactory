SELECT
    r.familyName AS RelativeFamilyName,
    r.givenName AS RelativeGivenName,
    r.middleName AS RelativeMiddleName,
    r.birthDate AS RelativeBirthDate,
    d.contactRelationship AS Relationship
FROM
    HPPersonDependant d
        JOIN
    HPPersonGeneric r ON d.HPRelatedPersonSysId = r.sysId
WHERE
        d.HPPersonGenericSysId = (
        SELECT sysId
        FROM HPPersonGeneric
        WHERE personId = 'test'
    );