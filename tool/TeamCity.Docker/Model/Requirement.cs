namespace TeamCity.Docker.Model;

public record Requirement(string Property, RequirementType Type, string Value);