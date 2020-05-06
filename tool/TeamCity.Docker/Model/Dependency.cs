namespace TeamCity.Docker.Model
{
    internal struct Dependency
    {
        public readonly DependencyType Type;
        
        public Dependency(DependencyType type) => Type = type;

        public override string ToString() => Type.ToString();
    }
}
