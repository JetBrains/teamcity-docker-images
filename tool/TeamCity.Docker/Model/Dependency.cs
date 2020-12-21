namespace TeamCity.Docker.Model
{
    internal readonly struct Dependency
    {
        private readonly DependencyType _type;
        
        public Dependency(DependencyType type) => _type = type;

        public override string ToString() => _type.ToString();
    }
}
